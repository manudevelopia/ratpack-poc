package info.developia.ratpack.poc

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.test.MainClassApplicationUnderTest
import spock.lang.Shared
import spock.lang.Specification

class AppSpec extends Specification {

    @Shared
    MainClassApplicationUnderTest app

    def setupSpec() {
        app = new MainClassApplicationUnderTest(App)
    }

    def "should return a greeting"() {
        when:
        String result = app.getHttpClient().getText("/")
        then:
        result == 'Hello World!'
    }

    def "should return a list of object as json"() {
        when:
        String result = app.getHttpClient().getText("/tasks")
        then:
        result == new ObjectMapper().writeValueAsString(App.getTasks())
    }

    def "should return an object as json"() {
        given:
        String id = "1"
        when:
        String result = app.getHttpClient().getText("/tasks/$id")
        then:
        result == new ObjectMapper().writeValueAsString(App.getTasks().get(0))
    }

    def cleanSpec() {
        app.close()
    }
}
