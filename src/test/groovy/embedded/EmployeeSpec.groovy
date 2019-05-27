package embedded

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EmployeeSpec extends Specification implements DomainUnitTest<Employee> {

    void "test invalid properties"() {
        when:
        def emp = new Employee(name: "John Snow", businessContact: [address: "The Wall"])

        and:
        emp.save()

        then:
        emp.hasErrors()

        and:
        emp.errors.allErrors.size() == 2
    }

    void "test valid properties and update it"() {
        when:
        def emp = new Employee(name: "John Snow",
                businessContact: [zipCode: "654321", address: "Winterfell"],
                personalContact: [ zipCode:  "654321", address: "Winterfell"]
        )

        and:
        emp.save()

        then:
        !emp.hasErrors()

        and:
        emp.businessContact.address == "Winterfell"

        when:
        emp.businessContact.address = "The Wall"
        emp.businessContact.zipCode = "123456"

        and:
        emp.save()

        then:
        emp.businessContact.address == "The Wall"
    }
}
