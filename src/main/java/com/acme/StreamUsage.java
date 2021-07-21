package com.acme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamUsage {

    private static final String CORE_MAPPING_URL = "CORE_MAPPING_URL";
    private static final String MAPPING_CLASS_URI = "MAPPING_CLASS_URI";
    private static final String TARGET_CLASS_ROLE = "TARGET_CLASS_ROLE";

    public boolean uglyStream(CClass cClass) {
        String classUri = cClass.getUri();
        return cClass.getModel().getContext().getModels().stream()
                .filter(cModel -> cModel.getUri().equals(CORE_MAPPING_URL)).findAny()
                .flatMap(cModel -> cModel.getClasses().stream().filter(cc -> cc.getUri().equals(MAPPING_CLASS_URI))
                        .findAny()
                        .flatMap(emClass -> emClass.getClassInstances().stream()
                                .map(emInst -> emInst.getValue(TARGET_CLASS_ROLE))
                                .filter(targetAttrExp -> targetAttrExp instanceof CReferenceExpressionImpl)
                                .map(targetAttrExp -> ((CReferenceExpressionImpl) targetAttrExp).getValue())
                                .filter(targetAttrValue -> targetAttrValue instanceof CClass
                                        && ((CClass) targetAttrValue).getUri().equals(classUri))
                                .findAny().map(o -> true)))
                .orElse(false);
    }

    public boolean lessUglyStream(CClass cClass) {
        String classUri = cClass.getUri();
        return cClass.getModel().getContext().getModels().stream()
            .filter(this::isCoreMapping)
            .findAny()
            .map(cModel -> cModel.getClasses().stream()
                .filter(this::isMappingClass)
                .findAny()
                .map(emClass -> isCClassTargetingGiven(emClass, classUri)).orElse(false))
            .orElse(false);
    }

    public boolean isCoreMapping(Model cModel) {
        return cModel.getUri().equals(CORE_MAPPING_URL);
    }

    public boolean isMappingClass(CClass cClass) {
        return cClass.getUri().equals(MAPPING_CLASS_URI);
    }

    public boolean isAttrCReferenceExpressionImpl(Object targetAttrExp) {
        return targetAttrExp instanceof CReferenceExpressionImpl;
    }

    public Predicate<Object> isTargetAttributeValueCCLass(String classUri) {
        return targetAttrValue -> targetAttrValue instanceof CReferenceExpressionImpl
                && ((CClass) targetAttrValue).getUri().equals(classUri);
    }

    public boolean isCClassTargetingGiven(CClass emClass, String classUri) {
        return emClass.getClassInstances().stream().map(emInst -> emInst.getValue(TARGET_CLASS_ROLE))
                .filter(this::isAttrCReferenceExpressionImpl)
                .map(targetAttrExp -> ((CReferenceExpressionImpl) targetAttrExp).getValue())
                .filter(isTargetAttributeValueCCLass(classUri)).findAny().map(o -> true).orElse(false);
    }

}