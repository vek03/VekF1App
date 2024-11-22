package com.example.vekf1app.models

data class Team(
    private var id: String? = null,
    private var nome: String,
    private var carro: String,
    private var imagem: String,
    private var corPrimaria: String,
    private var corSecundaria: String
) {
    fun getId(): String? {
        return id
    }

    fun setId(novoId: String) {
        id = novoId
    }

    fun getNome(): String {
        return nome
    }

    fun setNome(novoNome: String) {
        nome = novoNome
    }

    fun getCarro(): String {
        return carro
    }

    fun setCarro(novoCarro: String) {
        carro = novoCarro
    }

    fun getImagem(): String {
        return imagem
    }

    fun setImagem(novaImagem: String) {
        imagem = novaImagem
    }

    fun getCorPrimaria(): String {
        return corPrimaria
    }

    fun setCorPrimaria(novaCorPrimaria: String) {
        corPrimaria = novaCorPrimaria
    }

    fun getCorSecundaria(): String {
        return corSecundaria
    }

    fun setCorSecundaria(novaCorSecundaria: String) {
        corSecundaria = novaCorSecundaria
    }
}