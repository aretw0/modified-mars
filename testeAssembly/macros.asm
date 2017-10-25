#macro para exit
.macro done
	li $v0,10
	syscall
.end_macro

#macro para imprimir string
.macro prinThis(%str)
    li $v0, 4
    la $a0, %str #lembrar de la
    syscall
.end_macro

#macro para abrir arquivo - (arquivo, flags - 1 write - 0 read, modo de abertura) 
.macro openFile(%file,%flag,%mode)
    li $v0, 13
    la $a0, %file
    li $a1, %flag
    li $a2, %mode
    syscall
.end_macro

#macro para fechar arquivo
.macro closeFile()
    li $v0, 16
    add $a0,$s0, $zero # file descriptor devera estar salvo em $s0
    syscall
.end_macro

#macro para ler arquivo - (arquivo, end. do buffer de entrada, tamanho da leitura) 
.macro readFile(%buffer,%max)
    li $v0, 14
    add $a0,$s0,$zero # file descriptor devera estar salvo em $s0
    la $a1, %buffer
    li $a2, %max
    syscall
.end_macro

#macro para ler arquivo - (end. do buffer de saida, tamanho da escrita) 
.macro writeFile(%buffer,%max)
    li $v0, 15
    add $a0,$s0,$zero #file descriptor devera estar salvo em $s0
    la $a1, %buffer
    li $a2, %max
    syscall
.end_macro

#macro para fork
.macro fork (%val1, %val, %val2, %val3, %val4) 
	#par�metros referenciados %val
	li $v0,18	
	la $a0, %val1
	la $a1, %val
	la $v1, %val2
	la $a3, %val3
	la $a2, %val4
	syscall
.end_macro

#macro para processChange
.macro processChange
	li $a0, 1
	li $v0,19
	syscall
.end_macro

#macro para processTerminate
.macro processTerminate
	li $a0, 1
	li $v0,20
	syscall
.end_macro

.macro writing(%buffer,%max,%msg,%js,%jf)
    writeFile(%buffer,%max)
    bge $v0,0, %js # se for negativo deu falha
    prinThis(%msg)
    j %jf
.end_macro

.macro reading(%output,%max,%msg,%js,%jf)
    readFile(%output,%max)
    bge $v0,0, %js # se for negativo deu falha
    prinThis(%msg)
    j %jf
.end_macro

.macro opening(%file,%flag,%msg,%js,%jf)
    openFile(%file,%flag, 0)
    bge $v0,0, %js # se for negativo deu falha
    prinThis(%msg)
    j %jf
.end_macro

.macro closing(%msg)
    closeFile
    prinThis(%msg)
.end_macro
