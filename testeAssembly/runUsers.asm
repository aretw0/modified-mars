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
output1: .asciiz "___________________________________________"
output2: .asciiz "___________________________________________"
output3: .asciiz "___________________________________________"
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

Programa1: # escreve no dir1/arquivo11.txt
	opening(file11,9,warnO,rightOpen11,fimAll1)
rightOpen11:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(chunk1,11,warnW,rightWrite111,fimAll1)
rightWrite111:
	prinThis(writeOk)
	writing(chunk2,11,warnW,rightWrite112,fimAll1)
rightWrite112:
	prinThis(writeOk)
	writing(chunk3,11,warnW,rightWrite113,fimAll1)
rightWrite113:
	prinThis(writeOk)
	writing(chunk4,11,warnW,rightWrite114,fimAll1)
rightWrite114:
	prinThis(writeOk)
	closing(closeOk)
	processTerminate
	opening(file11,0,warnO,rightOpenR1,fimAll1)
rightOpenR1:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	reading(output1,44,warnR,rightRead1,fimAll1)
rightRead1:
	prinThis(readOk)
	prinThis(endl)
	prinThis(output1)
	prinThis(endl)
fimAll1:
	closing(closeOk)
	processTerminate

Programa2: # escreve no dir2/arquivo21.txt
	opening(file21,9,warnO,rightOpen21,fimAll2)
rightOpen21:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(chunk1,11,warnW,rightWrite211,fimAll2)
rightWrite211:
	prinThis(writeOk)
	writing(chunk2,11,warnW,rightWrite212,fimAll2)
rightWrite212:
	prinThis(writeOk)
	writing(chunk3,11,warnW,rightWrite213,fimAll2)
rightWrite213:
	prinThis(writeOk)
	writing(chunk4,11,warnW,rightWrite214,fimAll2)
rightWrite214:
	prinThis(writeOk)
	closing(closeOk)
	processTerminate
	opening(file21,0,warnO,rightOpenR2,fimAll2)
rightOpenR2:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	reading(output2,44,warnR,rightRead2,fimAll2)
rightRead2:
	prinThis(readOk)
	prinThis(endl)
	prinThis(output2)
	prinThis(endl)
fimAll2:
	closing(closeOk)
	processTerminate

Programa3: # escreve no dir3/arquivo31.txt
	opening(file31,9,warnO,rightOpen31,fimAll3)
rightOpen31:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(chunk1,11,warnW,rightWrite311,fimAll3)
rightWrite311:
	prinThis(writeOk)
	writing(chunk2,11,warnW,rightWrite312,fimAll3)
rightWrite312:
	prinThis(writeOk)
	writing(chunk3,11,warnW,rightWrite313,fimAll3)
rightWrite313:
	prinThis(writeOk)
	writing(chunk4,11,warnW,rightWrite314,fimAll3)
rightWrite314:
	prinThis(writeOk)
	closing(closeOk)
	processTerminate
	opening(file21,0,warnO,rightOpenR3,fimAll3)
rightOpenR3:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	reading(output3,44,warnR,rightRead3,fimAll3)
rightRead3:
	prinThis(readOk)
	prinThis(endl)
	prinThis(output3)
	prinThis(endl)
fimAll3:
	closing(closeOk)
	processTerminate
FimPrograma3:
