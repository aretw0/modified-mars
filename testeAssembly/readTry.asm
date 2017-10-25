.include "macros.asm"

.data
file1: .asciiz "dir1/arquivo11.txt" #com diretorio, precisa existir
file2: .asciiz "dir2/arquivo21.txt" #com diretorio, precisa existir
file3: .asciiz "dir3/arquivo31.txt" #com diretorio, precisa existir
output: .asciiz "___________________________________________"
endl: .asciiz "\n"
openOk: .asciiz "Arquivo aberto\n"
closeOk: .asciiz "Arquivo fechado\n"
writeOk: .asciiz "Escrita concluida\n"
readOk: .asciiz "Leitura concluida\n"
warnO: .asciiz "Falha na abertura\n"
warnW: .asciiz "Falha na escrita\n"
warnR: .asciiz "Falha na leitura\n"

.text
opening(file1,0,warnO,rightOpen,END)
rightOpen:
add $s0,$v0,$zero # salvando file description
prinThis(openOk)
reading(output,44,warnR,rightRead,END)
rightRead:
prinThis(readOk)
prinThis(output)
prinThis(endl)
closeFile
prinThis(closeOk)
END:
