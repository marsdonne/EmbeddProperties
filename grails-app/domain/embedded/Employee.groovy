package embedded

import grails.validation.Validateable

class Employee {

    static class Contact implements Validateable {
        String zipCode
        String address

        static constraints = {
            zipCode(matches: /[0-9]{6}/)
            address(blank: false)
        }
    }

    String name
    Contact businessContact
    Contact personalContact

    static embedded = ['businessContact', 'personalContact']

    static constraints = {
        name(blank: false)
        businessContact(validator: { it.validate() })
        personalContact(validator: { it.validate() })
    }
}
