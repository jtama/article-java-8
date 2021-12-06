package com.acme;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CasReelLambda {
    // tag::casReelAAmeliorerApres[]
public Map<String, List<Record>> getMap(List<Record> records, boolean sortByAnalysis) {
    Function<Record, String> classifier = sortByAnalysis ? Record::getAnalysisName : Record::getJobId; //<1>
    return records.stream().collect(Collectors.groupingBy(classifier)); //<2>
}
    // end::casReelAAmeliorerApres[]


    public static String TARGET_CLASS_ROLE;
    public static String MAPPING_CLASS_URI;
    public static String CORE_MAPPING_MODEL_URI;

    public boolean isExternal(CClass cClass) {
        return cClass.getModel().getContext().getModels().stream()
                .filter(cModel -> cModel.getUri().equals(CORE_MAPPING_MODEL_URI))
                .findAny()
                .flatMap(cModel -> cModel.getClasses().stream()
                        .filter(cc -> cc.getUri().equals(MAPPING_CLASS_URI))
                        .findAny().flatMap(emClass -> emClass.getClassInstances().stream()
                                .map(emInst -> emInst.getValue(TARGET_CLASS_ROLE))
                                .filter(targetAttrExpr -> targetAttrExpr instanceof CReferenceExpressionImpl)
                                .map(targetAttrExpr -> ((CReferenceExpressionImpl) targetAttrExpr).getValue())
                                .filter(targetAttrValue -> targetAttrValue instanceof CClass
                                        && ((CClass) targetAttrValue).getUri().equals(cClass.getUri()))
                                .findAny()
                                .map(o -> true)))
                .orElse(false);
    }

    public boolean isExternaldeux(CClass cClass) {
        List<Model> models = cClass.getModel().getContext().getModels();
        for (Model model : models) {
            if (model.getUri().equals(CORE_MAPPING_MODEL_URI)) {
                for (CClass classs : model.getClasses()) {
                    if (classs.getUri().equals(MAPPING_CLASS_URI)) {
                        for (CCObject instance : classs.getClassInstances()) {
                            Object targetClassRole = instance.getValue(TARGET_CLASS_ROLE);
                            if (targetClassRole instanceof CReferenceExpressionImpl &&
                                    ((CReferenceExpressionImpl) targetClassRole).getValue() instanceof CClass
                                    && ((CClass) ((CReferenceExpressionImpl) targetClassRole).getValue()).getUri().equals(cClass.getUri())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    class Material {

    }

    class Cotton extends Material {

        private String color;

        public String getColor() {
            return color;
        }

    }

    class Clothe {

    }

    class Sock extends Clothe {

        private Material material;

        public Material getMaterial() {
            return material;
        }

    }

    class Furniture {

        private List<Clothe> clothes;
        private String name;

        public List<Clothe> getClothes() {
            return clothes;
        }

        public String getName() {
            return name;
        }

    }

    class Room {

        private String name;
        private List<Furniture> furnitures;

        public String getName() {
            return name;
        }

        public List<Furniture> getFurnitures() {
            return furnitures;
        }

    }

    class Home {
        private List<Room> rooms;

        public List<Room> getRooms() {
            return rooms;
        }
    }


// tag::casReelAAmeliorerAvant[]
public boolean hasRedSocks(Home home) {
    return home.getRooms().stream()
            .filter(room -> room.getName().equals("bedroom"))
            .findAny()
            .flatMap(room -> room.getFurnitures().stream()
                    .filter(furniture -> furniture.getName().equals("sock drawer"))
                    .findAny().flatMap(furniture -> furniture.getClothes().stream()
                            .filter(clothe -> clothe instanceof Sock)
                            .map(clothe -> ((Sock) clothe).getMaterial())
                            .filter(material -> material instanceof Cotton
                                    && ((Cotton) material).getColor().equals("red"))
                            .findAny()
                            .map(o -> true)))
            .orElse(false);
}    // end::casReelAAmeliorerAvant[]


// tag::casReelAmelioreLambda[]
public boolean hasRedSock1(Home home) { // <1>
    return home.getRooms()
            .stream()
            .anyMatch(room -> isBedroom(room) && containsRedSock1(room));
}

private boolean containsRedSock1(Room room) {
    return room.getFurnitures()
            .stream()
            .anyMatch(furniture -> isSockDrawer(furniture) && containsRedSock1(furniture));
}

private boolean containsRedSock1(Furniture furniture) {
    return furniture.getClothes()
            .stream()
            .anyMatch(this::isARedSock);
}
// end::casReelAmelioreLambda[]

    // tag::casReelAmelioreLambda2[]
public boolean hasRedSock3(Home home) { // <3>
    Stream<Room> bedRooms = home.getRooms().stream()
            .filter(this::isBedroom); // Retains only bedrooms
    Stream<Furniture> sockDrawers = bedRooms
            .map(Room::getFurnitures) // Gets the list of furnitures
            .flatMap(List::stream) // Turns them to stream
            .filter(this::isSockDrawer); // Filter on sock drawer
    Stream<Clothe> clothes = sockDrawers
            .map(Furniture::getClothes) // Get clothes
            .flatMap(List::stream);
    return clothes
            .anyMatch(this::isARedSock); // Has at least one red sock
}
// end::casReelAmelioreLambda2[]


// tag::casReelAmelioreSansLambda[]
public boolean hasRedSock2(Home home) { // <2>
    for (Room room : home.getRooms()) {
        if (isBedroom(room) && containsRedSock2(room)) {
            return true;
        }
    }
    return false;
}

private boolean containsRedSock2(Room room) {
    for (Furniture furniture : room.getFurnitures()) {
        if (isSockDrawer(furniture) && containsRedSock2(furniture)) {
            return true;
        }
    }
    return false;
}

private boolean containsRedSock2(Furniture furniture) {
    for (Clothe clothe : furniture.getClothes()) {
        if (isARedSock(clothe)) {
            return true;
        }
    }
    return false;}

// end::casReelAmelioreSansLambda[]

// tag::casReelAmelioreCommun[]

private boolean isBedroom(Room room) {
    return room.getName().equals("bedroom");
}

private boolean isSockDrawer(Furniture furniture) {
    return furniture.getName().equals("sock drawer");
}

private boolean isARedSock(Clothe clothe) {
    return clothe instanceof Sock &&
            ((Sock) clothe).getMaterial() instanceof Cotton
            && ((Cotton) ((Sock) clothe).getMaterial()).getColor().equals("red");
}

// end::casReelAmelioreCommun[]
}
