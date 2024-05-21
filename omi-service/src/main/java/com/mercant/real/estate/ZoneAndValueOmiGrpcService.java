package com.mercant.real.estate;

import com.google.protobuf.Empty;
import com.mercant.real.estate.core.model.genericmodel.ValueAndZone;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactive;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactiveImpl;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

import java.util.function.Function;

@GrpcService
public class ZoneAndValueOmiGrpcService implements ZoneAndValueOmiGrpc {

    private static final Function<ValueAndZone, OmiZone> createOmiZone = valueAndZone -> OmiZone.newBuilder().build();
    private static final Function<ValueAndZone, OmiValue> createOmiValue = valueAndZone -> OmiValue.newBuilder().build();
    private final SinkOperationReactive sinkOperation = new SinkOperationReactiveImpl();

    @Override
    public Multi<ResponseValueAndZoneOmi> allValueAndZoneOmi(Empty request) {
        return sinkOperation.processAllFiles("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv", "C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv")
                .flatMap(valueAndZone -> Multi.createFrom()
                        .items(valueAndZone.getValue().stream().map(valueAndZone1 -> ResponseValueAndZoneOmi.newBuilder()
                                .setOmiZone(createOmiZone.apply(valueAndZone1))
                                .setOmiValue(createOmiValue.apply(valueAndZone1))
                                .build())));
    }
}
