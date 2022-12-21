import re as regex


#
# Miss Rate L1 = L2_DCR/LD_INS
# Miss Rate L2 = L3_DCR/L2_DCR
# Miss Rate L3 = L3_TCM/L3_DCR
#
# RAM Accesses = L3_TCM
# RAM Access Rate = L3_TCM/TOT_INS¬

lsts = []


lsts.append(open("no_vec_logs/output32_A.txt",   "r").read())
lsts.append(open("no_vec_logs/output128_A.txt",  "r").read())
lsts.append(open("no_vec_logs/output1024_A.txt", "r").read())
lsts.append(open("no_vec_logs/output2048_A.txt", "r").read())

lsts.append(open("no_vec_logs/output32_B.txt",   "r").read())
lsts.append(open("no_vec_logs/output128_B.txt",  "r").read())
lsts.append(open("no_vec_logs/output1024_B.txt", "r").read())
lsts.append(open("no_vec_logs/output2048_B.txt", "r").read())

switch = {
0: 32,
1: 128,
2: 1024,
3: 2048,
4: 32,
5: 128,
6: 1024,
7: 2048
}

elems = {}

for i in range(0,4):
    linesA = regex.split(r"\n", lsts[i])
    linesB = regex.split(r"\n", lsts[i+4])

    for j in range(2,len(linesA)-1):
        tmp = regex.split(r";", linesB[j])
        if len(tmp) > 1 and tmp[0] != "alg" and tmp[0] != "NoVec" :
            for k in range(2,len(tmp)-1):
                linesA[j] += tmp[k] + ";"
    
    elems[switch.get(i)] = linesA

int_elems = {}

for (c,v) in elems.items():
    j = 0
    acum_lin = []
    lst_elems = []
    elem = {}
    flag = 0
    for lst in v:
        if flag < 2:
            flag += 1
        else:
            lin = regex.split(r";", lst)
            if acum_lin == []:
                for i in range(1,len(lin)-1):
                    acum_lin.append(int(lin[i]))
            else:
                if int(lin[1]) < acum_lin[0]:
                    acum_lin[0] = int(lin[1])
                for i in range(2,len(lin)-1):
                    lin_i = int(lin[i])
                    acum_lin[i-1] += lin_i
            j += 1

            if j == 8:
                for i in range(1,len(acum_lin)):
                    acum_lin[i] /= 8
                print("Tam : Alg : Tempo : L2 DCR : L3 DCR : L2 TCM : L3 TCM : FP OPS : TOT INS : LD INS")
                print(str(c) + ':' + lin[0] + ':' + str(acum_lin))
                mr1 = acum_lin[1]/acum_lin[7]
                mr2 = acum_lin[2]/acum_lin[1]
                mr3 = acum_lin[4]/acum_lin[3]
                rai = acum_lin[4]/acum_lin[6]
                bt = acum_lin[4] * 64
                print("Miss Rate L1: " + str(mr1))
                print("Miss Rate L2: " + str(mr2))
                print("Miss Rate L3: " + str(mr3))
                print("RAM Accesses p/ Inst: " + str(rai))
                print("Bytes Transfered from RAM: " + str(bt))
                print("-------------------------")
                elem[lin[0]] = acum_lin
                lst_elems.append(elem)
                elem = {}
                j = 0
    #print(str(c) + ":" + str(lst_elems))
    int_elems[c] = lst_elems
    lst_elems = []


