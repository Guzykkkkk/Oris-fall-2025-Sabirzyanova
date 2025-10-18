package org.example.DTO.respons;

import lombok.Builder;
import org.example.DTO.DtoField;

import java.util.List;

@Builder
public class AuthRes {
    private List<DtoField> fields;
    private boolean yes;
}
