import re as regex

tol = 1.05


l32   = open("omp_logs/output24t.txt", "r").read()
#l128  = open("vec_logs/output128.txt", "r").read()
#l1024 = open("vec_logs/output1024_A.txt", "r").read()
#l2048 = open("vec_logs/output2048_A.txt", "r").read()

#Para o com 32
lst32 = {}
lines = regex.split(r"\n", l32)

for line in lines:
    tmp = regex.split(r";", line)

    if tmp[0] == "alg" or tmp[0] == "NoVec" or tmp[0] == "Vec" or tmp[0] == "\n":
        continue
    else:
        if tmp[0] in lst32:
            l = lst32.get(tmp[0])
            l.append(int(tmp[1]))
            lst32[tmp[0]] = l
        else:
            l = []
            l.append(int(tmp[1]))
            lst32[tmp[0]] = l

#Para o com 128
#lst128 = {}
#lines = regex.split(r"\n", l128)

#for line in lines:
#    tmp = regex.split(r";", line)
#    if tmp[0] == "alg" or tmp[0] == "NoVec" or tmp[0] == "Vec" or tmp[0] == "\n":
#        continue
#    else:
#        if tmp[0] in lst128:
#            l = lst128.get(tmp[0])
#            l.append(int(tmp[1]))
#            lst128[tmp[0]] = l
#        else:
#            l = []
#            l.append(int(tmp[1]))
#            lst128[tmp[0]] = l

##Para o com 1024
#lst1024 = {}
#lines = regex.split(r"\n", l1024)
#
#for line in lines:
#    tmp = regex.split(r";", line)
#    if tmp[0] == "alg" or tmp[0] == "NoVec" or tmp[0] == "Vec" or tmp[0] == "\n":
#        continue
#    else:
#        if tmp[0] in lst1024:
#            l = lst1024.get(tmp[0])
#            l.append(int(tmp[1]))
#            lst1024[tmp[0]] = l
#        else:
#            l = []
#            l.append(int(tmp[1]))
#            lst1024[tmp[0]] = l
#
##Para o com 2048
#lst2048 = {}
#lines = regex.split(r"\n", l2048)
#
#for line in lines:   
#   tmp = regex.split(r";", line)
#   if tmp[0] == "alg" or tmp[0] == "NoVec" or tmp[0] == "Vec" or tmp[0] == "\n":
#       continue
#   else:
#       if tmp[0] in lst2048:
#           l = lst2048.get(tmp[0])
#           l.append(int(tmp[1]))
#           lst2048[tmp[0]] = l
#       else:
#           l = []
#           l.append(int(tmp[1]))
#           lst2048[tmp[0]] = l

print("32: ")

for (k,l) in lst32.items():
    l.sort()
    for i in range(0,6):
        if l[i+1]<=l[i]*tol and l[i+2]<=l[i+1]*tol:
            print(k + ": " + str(l[i]) + ", " + str(l[i+1]) + ", " + str(l[i+2]))
            m = (l[i] + l[i+1] + l[i+2])/3
            print(k + ", average: " + str(m) + '\n')
            break

#print("128: ")
#
#for (k,l) in lst128.items():
#    l.sort()
#    for i in range(0,6):
#        if l[i+1]<=l[i]*tol and l[i+2]<=l[i+1]*tol:
#            print(k + ": " + str(l[i]) + ", " + str(l[i+1]) + ", " + str(l[i+2]))
#            m = (l[i] + l[i+1] + l[i+2])/3
#            print(k + ", average: " + str(m) + '\n')
#            break

#print("1024: ")
#
#for (k,l) in lst1024.items():
#    l.sort()
#    for i in range(0,6):
#        if l[i+1]<=l[i]*tol and l[i+2]<=l[i+1]*tol:
#            print(k + ": " + str(l[i]) + ", " + str(l[i+1]) + ", " + str(l[i+2]))
#            m = (l[i] + l[i+1] + l[i+2])/3
#            print(k + ", average: " + str(m) + '\n')
#            break
#
#print("2048: ")
#
#for (k,l) in lst2048.items():
#    s = lst2048
#    l.sort()
#    for i in range(0,6):
#        if l[i+1]<=l[i]*tol and l[i+2]<=l[i+1]*tol:
#            print(k + ": " + str(l[i]) + ", " + str(l[i+1]) + ", " + str(l[i+2]))
#            m = (l[i] + l[i+1] + l[i+2])/3
#            print(k + ", average: " + str(m) + '\n')
#            break

