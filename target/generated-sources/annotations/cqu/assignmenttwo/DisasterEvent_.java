package cqu.assignmenttwo;

import cqu.assignmenttwo.TypeOfDisaster;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-10-01T16:59:12", comments="EclipseLink-4.0.3.v20240522-rb5bf922d44efed420f3a09bc7fa4b015c369ea2a")
@StaticMetamodel(DisasterEvent.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class DisasterEvent_ { 

    public static volatile SingularAttribute<DisasterEvent, TypeOfDisaster> typeOfDisaster;
    public static volatile SingularAttribute<DisasterEvent, LocalDate> disasterDate;
    public static volatile SingularAttribute<DisasterEvent, String> reporterAddress;
    public static volatile SingularAttribute<DisasterEvent, String> disasterId;
    public static volatile SingularAttribute<DisasterEvent, Integer> reporterMobile;
    public static volatile SingularAttribute<DisasterEvent, Long> id;
    public static volatile SingularAttribute<DisasterEvent, String> reporterName;
    public static volatile SingularAttribute<DisasterEvent, String> disasterLocation;
    public static volatile SingularAttribute<DisasterEvent, String> disasterDescription;

}