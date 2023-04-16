package ecommerse.api.controller;

import ecommerse.api.model.ECard;
import ecommerse.api.model.Product;
import ecommerse.api.service.ECardService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/ecommerse")
@Validated
public class ECommerseController {

  @Autowired
  private ECardService eCardService;

  @Autowired
  private JCacheCacheManager jCacheCacheManager;


  /**
   * Create eCard.
   *
   * @return - ECard details
   */
  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping()
  public ResponseEntity<ECard> createEcard() {
    ECard result = eCardService.createECard();
    return new ResponseEntity(result, HttpStatus.CREATED);
  }

  /**
   * Get ECard by id.
   *
   * @param id - eCard id
   * @return - ECard details
   */
  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping(path = "/{id}")
  public ResponseEntity<ECard> getECard(@PathVariable("id") Long id) {
    if (jCacheCacheManager.getCache("eCard").get(id) == null){
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    ECard result = eCardService.getECardInfo(id);
    return new ResponseEntity(result, HttpStatus.OK);
  }

  /**
   * Delete ECard.
   *
   * @param id - eCard id
   */
  @ResponseStatus(code = HttpStatus.OK)
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteECard(@PathVariable("id") Long id) {
    if (jCacheCacheManager.getCache("eCard").get(id) == null){
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    eCardService.deleteECard(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Update ECard products.
   *
   * @param id - eCard id
   * @param products - List of products
   * @return - ECard details
   */
  @ResponseStatus(code = HttpStatus.OK)
  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ECard> updateECard(@PathVariable("id") Long id, @RequestBody @Valid List<Product> products) {
    if (jCacheCacheManager.getCache("eCard").get(id) == null){
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    ECard eCard = eCardService.getECardInfo(id);
    ECard result = eCardService.updateECardProduct(eCard, products, id);
    return new ResponseEntity(result, HttpStatus.OK);
  }
}
