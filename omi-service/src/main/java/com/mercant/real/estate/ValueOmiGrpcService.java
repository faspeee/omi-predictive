package com.mercant.real.estate;

import com.google.protobuf.Empty;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.sinkoperation.SinkOperation;
import com.mercant.real.estate.core.sinkoperation.SinkOperationImpl;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

import java.util.List;

@GrpcService
public class ValueOmiGrpcService implements ValueOmiGrpc {
    @Override
    public Multi<ResponseValueOmi> allValueOmi(Empty request) {
        SinkOperation sinkOperation = new SinkOperationImpl();
        // Just returns a stream emitting an item every 2ms and stopping after 10 items.
        List<OmiValue> omiValues = sinkOperation.readAllValue("C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv");
        return Multi.createFrom().iterable(omiValues)
                .select().first(omiValues.size())
                .map(omiValue -> ResponseValueOmi.newBuilder().setMessage(omiValue.toString()).build());

    }
}
