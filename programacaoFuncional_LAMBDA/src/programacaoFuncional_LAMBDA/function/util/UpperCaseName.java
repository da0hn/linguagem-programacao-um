package programacaoFuncional_LAMBDA.function.util;

import java.util.function.Function;

import programacaoFuncional_LAMBDA.entities.Product;

public class UpperCaseName implements Function<Product, String> {

	@Override public String apply(Product p) {
		return p.getName().toUpperCase();
	}

}
