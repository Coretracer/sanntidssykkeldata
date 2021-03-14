package no.navneet;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SanntidsSykkelDataApplication extends Application<SanntidsSykkelDataConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SanntidsSykkelDataApplication().run(args);
    }

    @Override
    public String getName() {
        return "sanntidssykkeldata";
    }

    @Override
    public void initialize(final Bootstrap<SanntidsSykkelDataConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final SanntidsSykkelDataConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
