package com.ez.tablebase.rest;


import com.ez.tablebase.rest.controller.TableController;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class RepositoryLinkProcessor implements ResourceProcessor<RepositoryLinksResource>
{
    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource)
    {
        resource.add(linkTo(TableController.class).withRel("/tablebase"));
        return resource;
    }
}
