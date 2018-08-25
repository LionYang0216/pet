package com.gzsoftware.pet.entity.po;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Base Class for persistence entities
 * @author pango leung
 *
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class BaseEntity implements Serializable {

}
