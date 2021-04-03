package br.com.zup.edu

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {

    val server = ServerBuilder
        .forPort(50051)
        .addService(FuncionarioEndpoint())
        .build()

    server.start()
    server.awaitTermination()
}

class FuncionarioEndpoint : FuncionarioServiceGrpc.FuncionarioServiceImplBase(){

    override fun cadastrar(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {

        println(request!!)


//        val nome = request?.nome
        var nome: String? = request.nome
        if(!request.hasField(FuncionarioRequest.getDescriptor().findFieldByName("nome"))){
            nome = "[???]"
        }

        val instante = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instante.epochSecond)
            .setNanos(instante.nano)
            .build()

        val response = FuncionarioResponse.newBuilder()
            .setNome(nome)
            .setCriadoEm(criadoEm)
            .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }
}