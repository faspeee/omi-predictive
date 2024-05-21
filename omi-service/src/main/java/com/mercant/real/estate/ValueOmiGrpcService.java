package com.mercant.real.estate;

import com.google.protobuf.Empty;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactive;
import com.mercant.real.estate.core.sinkoperation.reactive.SinkOperationReactiveImpl;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

@GrpcService
public class ValueOmiGrpcService implements ValueOmiGrpc {
    private final SinkOperationReactive sinkOperation = new SinkOperationReactiveImpl();

    @Override
    public Multi<ResponseValueOmi> allValueOmi(Empty request) {
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        return sinkOperation.readAllValue("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv")
                .map(omiValue -> ResponseValueOmi.newBuilder().setMessage(omiValue.toString()).build());
    }

    @Override
    public Multi<ResponseValueOmi> allValueOmiWithCondition(RequestValueWithCondition request) {
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        return sinkOperation.readValueWithCondition("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv",
                        fileObject -> {
                            if (fileObject instanceof OmiValue omiValue) {
                                return omiValue.getProv().equals("TO");
                            } else {
                                return false;
                            }
                        })
                .map(omiValue -> ResponseValueOmi.newBuilder().setMessage(omiValue.toString()).build());
    }

    @Override
    public Multi<ResponseValueOmi> processFileWithConditionValue(RequestValueWithCondition request) {
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        return sinkOperation.processFilesWithConditionValue("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv", "C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv",
                        fileObject -> {
                            if (fileObject instanceof OmiValue omiValue) {
                                return omiValue.getProv().equals("TO");
                            } else {
                                return false;
                            }
                        })
                .map(omiValue -> ResponseValueOmi.newBuilder().setMessage(omiValue.toString()).build());
    }

}
