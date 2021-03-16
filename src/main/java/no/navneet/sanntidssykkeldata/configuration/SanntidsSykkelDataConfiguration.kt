package no.navneet.sanntidssykkeldata.configuration

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.client.JerseyClientConfiguration
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration

data class SanntidsSykkelDataConfiguration(
    @JsonProperty("swagger")
    val swaggerBundleConfiguration: SwaggerBundleConfiguration,

    @JsonProperty("jerseyClient")
    val jerseyClientConfiguration: JerseyClientConfiguration,

    @JsonProperty("endpoint")
    val endpoint: Endpoint
) : Configuration()