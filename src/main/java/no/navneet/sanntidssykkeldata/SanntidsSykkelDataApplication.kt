package no.navneet.sanntidssykkeldata

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.dropwizard.Application
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.federecio.dropwizard.swagger.SwaggerBundle
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration
import no.navneet.sanntidssykkeldata.client.SanntidsDataClient
import no.navneet.sanntidssykkeldata.client.SykkelStativService
import no.navneet.sanntidssykkeldata.configuration.SanntidsSykkelDataConfiguration
import javax.ws.rs.client.Client


class SanntidsSykkelDataApplication : Application<SanntidsSykkelDataConfiguration?>() {
    override fun getName(): String {
        return "sanntidssykkeldata"
    }

    override fun initialize(bootstrap: Bootstrap<SanntidsSykkelDataConfiguration?>) {
        bootstrap.addBundle(object : SwaggerBundle<SanntidsSykkelDataConfiguration>() {
            override fun getSwaggerBundleConfiguration(configuration: SanntidsSykkelDataConfiguration): SwaggerBundleConfiguration {
                return configuration.swaggerBundleConfiguration
            }
        })
        bootstrap.objectMapper.registerKotlinModule()
    }

    override fun run(
        configuration: SanntidsSykkelDataConfiguration?,
        environment: Environment
    ) {
        val sykkelStativService = createSykkelStativService(environment, configuration)
        environment.jersey().register(StativetResource(sykkelStativService))
    }

    private fun createSykkelStativService(
        environment: Environment,
        configuration: SanntidsSykkelDataConfiguration?
    ): SykkelStativService {
        val workerClient = createWorkerClient(environment, configuration!!)
        val sanntidsDataClient = SanntidsDataClient(workerClient, configuration.endpoint.oslobySykkelUrl)
        return SykkelStativService(sanntidsDataClient)
    }


    private fun createWorkerClient(environment: Environment, configuration: SanntidsSykkelDataConfiguration): Client {
        val workerClient = JerseyClientBuilder(environment)
            .using(configuration.jerseyClientConfiguration)
            .build(name)
        environment.jersey().register(workerClient)

        return workerClient
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            SanntidsSykkelDataApplication().run(*args)
        }
    }
}