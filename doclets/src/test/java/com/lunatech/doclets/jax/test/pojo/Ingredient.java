package com.lunatech.doclets.jax.test.pojo;

/**
 * General type for pizza ingredients.
 * 
 * @see PojoPizza
 * @see PojoRESTPizza
 * @see PojoRESTPizza#getPojoPizza(String)
 * @since 1.2
 */
public class Ingredient {

  private boolean fresh;

  /**
   * Is the ingredient fresh?
   */
  public boolean isFresh() {
    return fresh;
  }

  public void setFresh(boolean fresh) {
    this.fresh = fresh;
  }

}
