import loader.BasicResourcesLoader;

public class Main {

	public static void main(String[] args) {
		// 1 - create a basic loader
		BasicResourcesLoader basicResourcesLoader = new BasicResourcesLoader();

		// 2 - add it your property file
		basicResourcesLoader.addPropertyFile("my_properties.prop");

		// 3 - Read the first property by its identifier.
		// try to read it as string (using a standard string reader provided by
		// the lib)
		String propertyVal = basicResourcesLoader.getProperty(
				"my.first.property",
				loader.standard.readers.direct.conversion.StringReader
						.getInstance());

		// 4 - show the property value on the output stream
		System.out.println(propertyVal);
	}

}
