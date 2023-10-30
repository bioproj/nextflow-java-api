package com.bioproj.scm;

import com.bioproj.scm.config.HubOptions;

import java.util.Map;

public class AssetManager {

    AssetManager() {
        this.providerConfigs = ProviderConfig.createDefault()
    }

    /**
     * Create a new asset manager with the specified pipeline name
     *
     * @param pipeline The pipeline to be managed by this manager e.g. {@code nextflow-io/hello}
     */
    AssetManager(String pipelineName, HubOptions cliOpts ) {
//        assert pipelineName

        // read the default config file (if available)
        def config = ProviderConfig.getDefault()
        // build the object
        build(pipelineName, config, cliOpts)
    }

    AssetManager( String pipelineName, Map config ) {
        assert pipelineName
        // build the object
        build(pipelineName, config)
    }

    AssetManager build(String pipelineName, Map config = null, HubOptions cliOpts = null ) {
        this.providerConfigs = ProviderConfig.createFromMap(config)

        this.project = resolveName(pipelineName)
        this.localPath = checkProjectDir(project)
        this.hub = checkHubProvider(cliOpts)
        this.provider = createHubProvider(hub)
        setupCredentials(cliOpts)
        validateProjectDir()

        return this
    }
}
