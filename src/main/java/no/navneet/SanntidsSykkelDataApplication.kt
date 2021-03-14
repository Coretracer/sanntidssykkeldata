package no.navneet

import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import java.lang.Exception

class SanntidsSykkelDataApplication : Application<SanntidsSykkelDataConfiguration?>() {
    override fun getName(): String {
        return "sanntidssykkeldata"
    }

    override fun initialize(bootstrap: Bootstrap<SanntidsSykkelDataConfiguration?>) {
        // TODO: application initialization
    }

    override fun run(
        configuration: SanntidsSykkelDataConfiguration?,
        environment: Environment
    ) {
        // TODO: implement application
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            SanntidsSykkelDataApplication().run(*args)
        }
    }
}