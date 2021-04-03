package br.com.zup.edu

import io.grpc.ManagedChannelBuilder

fun main() {

    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val client = FuncionarioServiceGrpc.newBlockingStub(channel)

    val request = FuncionarioRequest.newBuilder()
        .setNome("Yuri Matheus")
        .setCpf("000.000.000-00")
        .setIdade(23)
        .setSalario(2000.20)
        .setAtivo(true)
        .setCargo(Cargo.QA)
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
                .setLogradouro("Rua das Tabajaras")
                .setCep("00000-000")
                .setComplemento("Casa 20")
                .build()
        )
        .build()


    val response = client.cadastrar(request)
    println(response)
}