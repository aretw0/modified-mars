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

# linefeed
endl: .asciiz "\n"

# Saida
output1: .space 1024
output2: .space 1024
output3: .space 1024


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
body2: .asciiz "The slowly red fox was taken by the lazy dog" # tem 44 caracteres
body3: .asciiz "The quick brown dog knocked the lazy red fox" # tem 44 caracteres

bodySp: .asciiz "Eu amei pagar a cadeira de sistemas operacionais com meu querido professor Silvio!"
# 82

bodySp1: .asciiz "Work it, make it, do it, makes us\nHarder, better, faster, stronger\nMore than, our, hour, ever\nEver, after, work is, over\nWork it, make it, do it, makes us\nHarder, better, faster, stronger\nWork it harder, make it better\nDo it faster, makes us stronger\nMore than ever, hour after hour\nWork is never over\nWork it harder, make it better\nDo it faster, makes us stronger\nMore than ever, hour after hour\nWork is never over" 
# 428

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
	writing(body1,44,warnW,rightWrite11,fimAll1)
rightWrite11:
	prinThis(writeOk)
	closing(closeOk)
	opening(file12,9,warnO,rightOpen12,fimAll1)
rightOpen12:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body1,44,warnW,rightWrite12,fimAll1)
rightWrite12:
	prinThis(writeOk)
	closing(closeOk)
	opening(file13,9,warnO,rightOpen13,fimAll1)
rightOpen13:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body1,44,warnW,rightWrite13,fimAll1)
rightWrite13:
	prinThis(writeOk)
	closing(closeOk)
	opening(file14,9,warnO,rightOpen14,fimAll1)
rightOpen14:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body1,44,warnW,rightWrite14,fimAll1)
rightWrite14:
	prinThis(writeOk)
	closing(closeOk)
	opening(file15,9,warnO,rightOpen15,fimAll1)
rightOpen15:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(bodySp1,428,warnW,rightWrite15,fimAll1)
rightWrite15:
	prinThis(writeOk)
	closing(closeOk)
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
	closing(closeOk)
fimAll1:
	closing(closeOk)
	processTerminate

Programa2: # escreve no dir2/arquivo21.txt
	opening(file21,9,warnO,rightOpen21,fimAll2)
rightOpen21:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body2,44,warnW,rightWrite21,fimAll2)
rightWrite21:
	prinThis(writeOk)
	closing(closeOk)
	opening(file22,9,warnO,rightOpen22,fimAll2)
rightOpen22:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body2,44,warnW,rightWrite22,fimAll2)
rightWrite22:
	prinThis(writeOk)
	closing(closeOk)
	opening(file23,9,warnO,rightOpen23,fimAll2)
rightOpen23:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body2,44,warnW,rightWrite23,fimAll2)
rightWrite23:
	prinThis(writeOk)
	closing(closeOk)
	opening(file24,9,warnO,rightOpen24,fimAll2)
rightOpen24:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body1,44,warnW,rightWrite24,fimAll2)
rightWrite24:
	prinThis(writeOk)
	closing(closeOk)
	opening(file25,9,warnO,rightOpen25,fimAll2)
rightOpen25:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(bodySp,82,warnW,rightWrite25,fimAll2)
rightWrite25:
	prinThis(writeOk)
	closing(closeOk)
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
	writing(body3,44,warnW,rightWrite31,fimAll3)
rightWrite31:
	prinThis(writeOk)
	closing(closeOk)
	opening(file32,9,warnO,rightOpen32,fimAll3)
rightOpen32:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body3,44,warnW,rightWrite32,fimAll3)
rightWrite32:
	prinThis(writeOk)
	closing(closeOk)
	opening(file33,9,warnO,rightOpen33,fimAll3)
rightOpen33:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body3,44,warnW,rightWrite33,fimAll3)
rightWrite33:
	prinThis(writeOk)
	closing(closeOk)
	opening(file34,9,warnO,rightOpen34,fimAll3)
rightOpen34:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body3,44,warnW,rightWrite34,fimAll3)
rightWrite34:
	prinThis(writeOk)
	closing(closeOk)
	opening(file35,9,warnO,rightOpen35,fimAll3)
rightOpen35:
	add $s0,$v0,$zero # salvando file description
	prinThis(openOk)
	writing(body2,44,warnW,rightWrite35,fimAll3)
rightWrite35:
	prinThis(writeOk)
	closing(closeOk)
	opening(file31,0,warnO,rightOpenR3,fimAll3)
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
