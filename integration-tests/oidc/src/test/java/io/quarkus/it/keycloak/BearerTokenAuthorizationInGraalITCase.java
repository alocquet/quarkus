package io.quarkus.it.keycloak;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.SubstrateTest;

@QuarkusTestResource(KeycloakTestResource.class)
@SubstrateTest
public class BearerTokenAuthorizationInGraalITCase extends BearerTokenAuthorizationTest {
}
