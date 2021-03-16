package no.navneet.sanntidssykkeldata

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import no.navneet.sanntidssykkeldata.api.ResponseWrapper
import no.navneet.sanntidssykkeldata.client.SykkelStativService
import java.time.Instant
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("/")
@Api("/sanntidssykkelservice")
class StativetResource(val sykkelStativService: SykkelStativService) {

    @Path("stativer")
    @Produces("application/json")
    @ApiOperation(value = "Get all stativer", response = StativetResource::class)
    @GET
    fun getStativer(): Response {

        val stativResponseWrapper = ResponseWrapper(
            Instant.now().toEpochMilli(),
            sykkelStativService.getSykkelStativ()
        )

        return Response.ok(stativResponseWrapper).build()
    }
}
