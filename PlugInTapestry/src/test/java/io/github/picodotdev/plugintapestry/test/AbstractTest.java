package io.github.picodotdev.plugintapestry.test;

import com.formos.tapestry.testify.core.TapestryTester;
import com.formos.tapestry.testify.junit4.TapestryTest;

import io.github.picodotdev.plugintapestry.test.services.TestModule;

public abstract class AbstractTest extends TapestryTest {

	private static final TapestryTester SHARED_TESTER = new TapestryTester("io.github.picodotdev.plugintapestry", "app", "src/main/webapp", TestModule.class);

	public AbstractTest() {
		super(SHARED_TESTER);
	}
}
