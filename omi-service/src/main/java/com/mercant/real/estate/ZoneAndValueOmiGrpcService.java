package com.mercant.real.estate;

import com.google.protobuf.Empty;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactive;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactiveImpl;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

import static com.mercant.real.estate.util.BuilderUtil.createOmiValueFromValueAndZone;
import static com.mercant.real.estate.util.BuilderUtil.createOmiZoneFromValueAndZone;

@GrpcService
public class ZoneAndValueOmiGrpcService implements ZoneAndValueOmiGrpc {


    private final SinkOperationReactive sinkOperation = new SinkOperationReactiveImpl();

    @Override
    public Multi<ResponseValueAndZoneOmi> allValueAndZoneOmi(Empty request) {
        return sinkOperation.processAllFiles("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv", "C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv")
                .flatMap(valueAndZone -> Multi.createFrom()
                        .items(valueAndZone.getValue().stream().map(valueAndZone1 -> ResponseValueAndZoneOmi.newBuilder()
                                .setOmiZone(createOmiZoneFromValueAndZone.apply(valueAndZone1))
                                .setOmiValue(createOmiValueFromValueAndZone.apply(valueAndZone1))
                                .build())));
    }
}