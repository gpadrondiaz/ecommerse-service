package ecommerse.api.service;

import ecommerse.api.ECommerseApplication;
import ecommerse.api.model.ECard;
import ecommerse.api.model.Product;
import java.util.Arrays;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.jcache.JCacheCacheManager;


@SpringBootTest(classes = ECommerseApplication.class)
public class ECardServiceTest {

  @Autowired
  private JCacheCacheManager jCacheCacheManager;

  @Autowired
  private ECardService eCardService;

  @Test
  public void testCreateECard() {
    ECard eCard = eCardService.createECard();
    Assertions.assertNotNull(eCard);
    Assertions.assertNotNull(jCacheCacheManager.getCache("eCard").get(eCard.getId()));
  }

  @Test
  public void testDeleteECard() {
    ECard eCard = eCardService.createECard();
    Assertions.assertNotNull(eCard);
    Assertions.assertNotNull(jCacheCacheManager.getCache("eCard").get(eCard.getId()));

    eCardService.deleteECard(eCard.getId());
    Assertions.assertNull(jCacheCacheManager.getCache("eCard").get(eCard.getId()));
  }

  @Test
  public void testGetECard() {
    ECard eCard = eCardService.createECard();
    eCardService.getECardInfo(eCard.getId());
    Assertions.assertNotNull(jCacheCacheManager.getCache("eCard").get(eCard.getId()));
  }

  @Test
  public void testUpdateECard() {
    ECard eCard = eCardService.createECard();
    Assertions.assertNotNull(jCacheCacheManager.getCache("eCard").get(eCard.getId()));
    Assertions.assertTrue(((ECard)jCacheCacheManager.getCache("eCard").get(eCard.getId()).get()).getProducts().isEmpty());

    eCardService.updateECardProduct(
        eCard, Arrays.asList(new Product(1l, 10l, "t-shirts")), eCard.getId());
    Assertions.assertFalse(((ECard)jCacheCacheManager.getCache("eCard").get(eCard.getId()).get()).getProducts().isEmpty());
  }
}
