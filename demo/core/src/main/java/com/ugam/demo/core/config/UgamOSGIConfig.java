package com.ugam.demo.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name="Demo - OSGi Configuration",
        description = " OSGi Configuration with run modes.")
public @interface UgamOSGIConfig {

    @AttributeDefinition(
            name = "Service ID",
            description = "Enter service id.",
            type = AttributeType.INTEGER)
    int serviceID() default 0;

    @AttributeDefinition(
            name = "Service Name",
            description = "Enter service name.",
            type = AttributeType.STRING)
    public String serviceName() default "Demo Service";

    @AttributeDefinition(
            name = "Is Service",
            description = "Select Is Service.",
            type = AttributeType.BOOLEAN)
    public boolean isService() default false;

    @AttributeDefinition(
            name = "Service Countries",
            description = "Add Service Countries.",
            type = AttributeType.STRING)
    String[] serviceCountry() default {"us","in"};

    @AttributeDefinition(
            name = "Run Modes",
            description = "Add Run Modes.",
            options = {
                    @Option(label = "Author",value = "author"),
                    @Option(label = "Publish",value = "publish"),
            },
            type = AttributeType.STRING)
    String serviceRunMode() default "author";

}
