package com.alm.connector;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.alm.connector");

        noClasses()
            .that()
            .resideInAnyPackage("com.alm.connector.service..")
            .or()
            .resideInAnyPackage("com.alm.connector.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.alm.connector.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
