.include "macros.asm"

.data
# Avisos sucesso
openOk: .asciiz "Arquivo aberto\n"
closeOk: .asciiz "Arquivo fechado\n"
writeOk: .asciiz "Escrita concluída\n"
readOk: .asciiz "Leitura concluída\n"

# Avisos falha
warnO: .asciiz "Falha na abertura\n"
warnW: .asciiz "Falha na escrita\n"
warnR: .asciiz "Falha na leitura\n"

# Saida
output: .asciiz ""
# linefeed
endl: .asciiz "\n"

# Definição de arquivos
file11: .asciiz "dir1/arquivo11.txt" #dir principal
file21: .asciiz "dir2/arquivo21.txt" 
file31: .asciiz "dir3/arquivo31.txt"

file12: .asciiz "dir1/dir11/arquivo12.txt" #dir interno ao principal
file22: .asciiz "dir2/dir21/arquivo22.txt"
file32: .asciiz "dir3/dir31/arquivo32.txt"

file13: .asciiz "dir1/dir12/arquivo13.txt"
file23: .asciiz "dir2/dir22/arquivo23.txt"
file33: .asciiz "dir3/dir32/arquivo33.txt"

file14: .asciiz "dir1/dir13/arquivo14.txt"
file24: .asciiz "dir2/dir23/arquivo24.txt"
file34: .asciiz "dir3/dir33/arquivo34.txt"

file15: .asciiz "dir1/dir11/dir111/arquivo15.txt" # dir interno ao interno ao principal
file25: .asciiz "dir2/dir21/dir211/arquivo25.txt"
file35: .asciiz "dir3/dir31/dir311/arquivo35.txt"

# body writing
body1: .asciiz "The quick brown fox jumps over the lazy dog " # tem 44 caracteres
chunk1: .asciiz "The quick b" # tem 11 caracteres
chunk2: .asciiz "rown fox ju" # tem 11 caracteres
chunk3: .asciiz "mps over th" # tem 11 caracteres
chunk4: .asciiz "e lazy dog " # tem 11 caracteres

.text
#criação dos processos
	fork(Programa1, Programa2, 8, 0, 15)
	fork(Programa2, Programa3, 10, 0, 15)
	fork(Programa3, FimPrograma3, 12, 0, 15)
	fork(Idle,Programa1, 6, 0, 15)		
#escalonando o primeiro processo	
	processChange

Idle:
	loop:
	add $zero,$zero,$zero
	j loop

Programa1: # programa tenta escrever no arquivo1
	opening(file11,9,warnO,rightOpen11,fim11)
rightOpen11:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(chunk1,11,warnW,rightWrite111,fim11)
rightWrite111:
	prinThis(writeOk)
	writing(chunk2,11,warnW,rightWrite112,fim11)
rightWrite112:
	prinThis(writeOk)
	writing(chunk3,11,warnW,rightWrite113,fim11)
rightWrite113:
	prinThis(writeOk)
	writing(chunk4,11,warnW,rightWrite114,fim11)
rightWrite114:
	prinThis(writeOk)
fim11:
	closing(closeOk)

Programa2:
	opening(file21,9,warnO,rightOpen21,fim21)
rightOpen21:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(chunk1,11,warnW,rightWrite211,fim21)
	j fim21
rightWrite211:
	prinThis(writeOk)
	writing(chunk2,11,warnW,rightWrite212,fim21)
rightWrite212:
	prinThis(writeOk)
	writing(chunk3,11,warnW,rightWrite213,fim21)
rightWrite213:
	prinThis(writeOk)
	writing(chunk4,11,warnW,rightWrite214,fim21)
rightWrite214:
	prinThis(writeOk)
fim21:
	closing(closeOk)

Programa3:
	opening(file31,9,warnO,rightOpen31,fim31)
rightOpen31:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(chunk1,11,warnW,rightWrite311,fim31)
rightWrite311:
	prinThis(writeOk)
	writing(chunk2,11,warnW,rightWrite312,fim31)
	j fim31
rightWrite312:
	prinThis(writeOk)
	writing(chunk3,11,warnW,rightWrite313,fim31)
rightWrite313:
	prinThis(writeOk)
	writing(chunk4,11,warnW,rightWrite314,fim31)
rightWrite314:
	prinThis(writeOk)
fim31:
	closing(closeOk)
FimPrograma3: