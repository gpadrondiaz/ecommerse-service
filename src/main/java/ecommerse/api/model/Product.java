package ecommerse.api.model;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {
  @NotNull(message = "The id is required")
  private Long id;

  @NotNull(message = "The amount is required")
  private Long amount;

  @NotEmpty(message = "The description is required")
  private String description;
}
