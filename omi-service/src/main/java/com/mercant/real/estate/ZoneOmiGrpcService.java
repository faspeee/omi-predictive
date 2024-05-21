package com.mercant.real.estate;

import com.google.protobuf.Empty;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactive;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactiveImpl;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

@GrpcService
public class ZoneOmiGrpcService implements ZoneOmiGrpc {
    private final SinkOperationReactive sinkOperation = new SinkOperationReactiveImpl();

    @Override
    public Multi<ResponseZoneOmi> allZoneOmi(Empty request) {
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        return sinkOperation.readAllZone("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv")
                .map(omiValue -> ResponseZoneOmi.newBuilder().setMessage(omiValue.toString()).build());
    }

    @Override
    public Multi<ResponseZoneOmi> allZoneOmiWithCondition(RequestZoneWithCondition request) {
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        return sinkOperation.readZoneWithCondition("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv",
                        fileObject -> {
                            if (fileObject instanceof OmiZone omiZone) {
                                return omiZone.getProv().equals("TO");
                            } else {
                                return false;
                            }
                        })
                .map(omiValue -> ResponseZoneOmi.newBuilder().setMessage(omiValue.toString()).build());
    }

    @Override
    public Multi<ResponseZoneOmi> processFileWithConditionZone(RequestZoneWithCondition request) {
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        return sinkOperation.processFilesWithConditionZone("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv", "C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv",
                        fileObject -> {
                            if (fileObject instanceof OmiZone omiZone) {
                                return omiZone.getProv().equals("TO");
                            } else {
                                return false;
                            }
                        })
                .map(omiValue -> ResponseZoneOmi.newBuilder().setMessage(omiValue.toString()).build());
    }
}
