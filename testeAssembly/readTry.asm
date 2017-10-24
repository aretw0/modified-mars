.include "macros.asm"

.data
file1: .asciiz "testFiles/arquivo2.txt" #com diretorio, precisa existir
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

openFile(file1, 0, 0)
bge $v0,0, rightOpen # se for negativo deu falha
prinThis(warnO)
j END
rightOpen: add $s0,$v0,$zero # salvando file description
prinThis(openOk)
readFile(output,44)
bge $v0,0, rightRead # se for negativo deu falha
prinThis(warnR)
j END
rightRead: prinThis(output)
closeFile
prinThis(closeOk)

END:
processTerminate