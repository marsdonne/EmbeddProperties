package embedded

import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

import static com.mongodb.client.model.Filters.eq

@Integration
@Rollback
class EmployeeIntegrationSpec extends Specification {

    void "test update passed, but properties unchanged"() {
        when:
        def emp = new Employee(name: "John Snow",
                businessContact: [zipCode: "654321", address: "Winterfell"],
                personalContact: [zipCode: "654321", address: "Winterfell"]
        )

        and:
        emp.save(flush: true)

        then:
        !emp.hasErrors()

        and:
        emp.businessContact.address == "Winterfell"

        and:
        Employee.collection.find(eq("name", "John Snow")).first().get("businessContact")["address"] == "Winterfell"

        when:
        emp.name = "Aegon Targaryen"
        emp.businessContact.address = "The Wall"

        and:
        emp.save(flush: true)

        then:
        emp.businessContact.address == "The Wall"

        and:
        Employee.collection.find(eq("name", "John Snow")).first() == null

        and:
        Employee.collection.find(eq("name", "Aegon Targaryen")).first().get("businessContact")["address"] == "The Wall"
    }
}
