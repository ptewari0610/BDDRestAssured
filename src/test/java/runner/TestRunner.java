package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

import static io.cucumber.testng.CucumberOptions.SnippetType.CAMELCASE;

@CucumberOptions(
        features="src/test/resources",
        glue = "glue",
        tags = "@Login or @Product or @Order or @ViewOrder or @DeleteOrder or @DeleteProduct",
   //     plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","pretty"},
        monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests{



//        /** Calling method to attach customized data to Extent Report
//         */
//        @AfterClass
//        public static void stampEnvironmentDetailsOnExtentReport() {
//            ReportUtils.generateReportMetadata();
//        }


//        /** Double dimensional Object Array for running scenarios in parallel
//         @return scenarios This return scenarios
//         */
//        @Override
//        @DataProvider(parallel = false)
//        public Object[][] scenarios(){
//            return super.scenarios();
//        }

}
