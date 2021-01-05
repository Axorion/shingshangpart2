module shingshang.zip_expanded {
	requires transitive javafx.controls;
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires javafx.media;
	
	requires junit;
	requires org.junit.jupiter.api;
	

	exports presentation;
	exports application;
}