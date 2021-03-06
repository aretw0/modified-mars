
.include "macros.asm"

.data
file1: .asciiz "a/b/c/arquivo2.txt" #com diretorio, precisa existir
#file1: .asciiz "arquivo1.txt" # sem diret�rio escreve na raiz do projeto
body1: .asciiz "The quick brown fox jumps over the lazy dog" # tem 44 caracteres
file2: .asciiz "arquivo2.txt"
file3: .asciiz "arquivo3.txt"
output: .asciiz ""
openOk: .asciiz "Arquivo aberto\n"
closeOk: .asciiz "Arquivo fechado\n"
writeOk: .asciiz "Escrita conclu�da\n"
readOk: .asciiz "Leitura conclu�da\n"
warnO: .asciiz "Falha na abertura\n"
warnW: .asciiz "Falha na escrita\n"
warnR: .asciiz "Falha na leitura\n"

.text

#cria��o dos processos
	fork(Programa1, Programa2, 8, 0, 15)
	fork(Programa2, FimPrograma2, 10, 0, 15)		

#esclonando o primeiro processo	
	processChange


Programa1:
	
openFile(file1, 1, 0)
bge $v0,0, rightOpen # se for negativo deu falha
prinThis(warnO)
j END
rightOpen: add $s0,$v0,$zero # salvando file description
prinThis(openOk)
writeFile(body1,44)
bge $v0,0, rightWrite # se for negativo deu falha
prinThis(warnW)
j END
rightWrite: prinThis(writeOk)
closeFile
prinThis(closeOk)

END:
processTerminate

Programa2:

	addi $s1, $zero, -1 # valor inicial do contador

	addi $s2, $zero, -100 # valor limite do contador

	loop2: addi $s1, $s1, -1

	beq $s1, $s2, fim2

	#processChange

	j loop2

fim2: processTerminate

FimPrograma2:
