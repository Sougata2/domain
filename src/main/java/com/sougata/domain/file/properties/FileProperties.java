package com.sougata.domain.file.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String location;
    private Set<String> allowedExtensions;
}
