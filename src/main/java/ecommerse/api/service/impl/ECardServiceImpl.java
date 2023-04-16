package ecommerse.api.service.impl;

import ecommerse.api.model.ECard;
import ecommerse.api.model.Product;
import ecommerse.api.service.ECardService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.stereotype.Service;

@Service
public class ECardServiceImpl implements ECardService {

  @Autowired
  private JCacheCacheManager jCacheCacheManager;

  /**
   * Create ECard.
   *
   * @return - ECard detaile
   */
  public ECard createECard() {
    final Random rand = new Random();
    final Long eCardId = Long.valueOf(rand.nextInt(999));
    ECard eCard = new ECard(eCardId, new ArrayList<Product>());
    jCacheCacheManager.getCache("eCard").put(eCardId, eCard);
    return eCard;
  }

  /**
   * Get ECard by id.
   *
   * @param id - eCard id
   * @return - ECard details
   */
  @Cacheable(cacheNames = "eCard", key = "#id")
  public ECard getECardInfo(Long id){
    return (ECard) jCacheCacheManager.getCache("eCard").get(id).get();
  }

  /**
   * Delete ECard by id.
   *
   * @param id - eCard id
   */
  @CacheEvict(cacheNames = "eCard", key = "#id")
  public void deleteECard(Long id) {
  }

  /**
   * Update ECard products; if product Id exists, it is replaced.
   *
   * @param id - eCard id
   * @param products - List of products
   * @return - ECard details
   */
  @CachePut(cacheNames = "eCard", key = "#id")
  public ECard updateECardProduct(ECard eCard, List<Product> products, Long id){
    final List<Product> productDtos = eCard.getProducts();
    final List<Product> existingProducts = productDtos.stream()
        .filter(prod -> products.stream().map(Product::getId)
            .collect(Collectors.toList()).contains(prod.getId()))
        .collect(Collectors.toList());

    if (!existingProducts.isEmpty()) {
      productDtos.removeAll(existingProducts);
    }
    productDtos.addAll(products);
    eCard.setProducts(productDtos);
    return eCard;
  }
}
