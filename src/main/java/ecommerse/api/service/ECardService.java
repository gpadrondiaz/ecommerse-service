package ecommerse.api.service;

import ecommerse.api.model.ECard;
import ecommerse.api.model.Product;
import java.util.List;

public interface ECardService {

  ECard createECard();
  void deleteECard(Long eCardId);
  ECard getECardInfo(Long eCardId);
  ECard updateECardProduct(ECard eCard, List<Product> products, Long id);

}
