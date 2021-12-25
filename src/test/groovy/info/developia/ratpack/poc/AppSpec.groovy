package info.developia.ratpack.poc

import ratpack.test.MainClassApplicationUnderTest
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

@Ignore
class AppSpec extends Specification {

    @Shared
    MainClassApplicationUnderTest app

    def setupSpec() {
        app = new MainClassApplicationUnderTest(App)
    }

//    def "should return a greeting"() {
//        when:
//        String result = app.getHttpClient().getText("/")
//        then:
//        result == 'Hello World!'
//    }

//    def "should return a list of object as json"() {
//        when:
//        String result = app.getHttpClient().getText("/tasks")
//        then:
//        result == new ObjectMapper().writeValueAsString(App.getTasks())
//    }
//
//    def "should return an object as json"() {
//        given:
//        String id = "91af8603-7471-4366-a5fc-f44d1495e295"
//        when:
//        String result = app.getHttpClient().getText("/tasks/$id")
//        then:
//        result == new ObjectMapper().writeValueAsString(App.getTasks().get(0))
//    }

    def "should not return an object if not found"() {
        given:
        String id = "notExistingId"
        when:
        int result = app.getHttpClient().get("/tasks/$id").getStatusCode()
        then:
        result == 404
    }

    def cleanSpec() {
        app.close()
    }
}
