import java.util.*;
class A{
void simple(char c)
{
    switch(c)
    {
        case '0':printf("0");
                break;
        case '1':printf("(1)");
                break;
        case '2':printf("((2))");
                break;
        case '3':printf("(((3)))");
                break;
        case '4':printf("((((4))))");
                break;
        case '5':printf("(((((5)))))");
                break;
        case '6':printf("((((((6))))))");
                break;
        case '7':printf("(((((((7)))))))");
                break;
        case '8':printf("((((((((8))))))))");
                break;
        case '9':printf("(((((((((9)))))))))");
                break;
    }
}
void simple1(char c)
{
    switch(c)
    {
        case '0':printf("00");
                break;
        case '1':printf("(11)");
                break;
        case '2':printf("((22))");
                break;
        case '3':printf("(((33)))");
                break;
        case '4':printf("((((44))))");
                break;
        case '5':printf("(((((55)))))");
                break;
        case '6':printf("((((((66))))))");
                break;
        case '7':printf("(((((((77)))))))");
                break;
        case '8':printf("((((((((88))))))))");
                break;
        case '9':printf("(((((((((99)))))))))");
                break;
    }
}
void simple2(char c)
{
    switch(c)
    {
        case '0':printf("000");
                break;
        case '1':printf("(111)");
                break;
        case '2':printf("((222))");
                break;
        case '3':printf("(((333)))");
                break;
        case '4':printf("((((444))))");
                break;
        case '5':printf("(((((555)))))");
                break;
        case '6':printf("((((((666))))))");
                break;
        case '7':printf("(((((((777)))))))");
                break;
        case '8':printf("((((((((888))))))))");
                break;
        case '9':printf("(((((((((999)))))))))");
                break;
    }
}
void simple3(char c)
{
    switch(c)
    {
        case '0':printf("0000");
                break;
        case '1':printf("(1111)");
                break;
        case '2':printf("((2222))");
                break;
        case '3':printf("(((3333)))");
                break;
        case '4':printf("((((4444))))");
                break;
        case '5':printf("(((((5555)))))");
                break;
        case '6':printf("((((((6666))))))");
                break;
        case '7':printf("(((((((7777)))))))");
                break;
        case '8':printf("((((((((8888))))))))");
                break;
        case '9':printf("(((((((((9999)))))))))");
                break;
    }
}
public static void main(String args[])
{
    char str[10][100];
    int T;
    int i,j,z;
    scanf("%d",&T);
    for(z=1;z<=T;z++)
    {
        scanf("%s",str[z]);
    }
    for(i=1;i<=T;i++)
    {
        printf("Case #%d: ",i);
        for(j=0;str[i][j]!='\0';j++)
        {
            if(str[i][j+1]!='\0' && str[i][j]==str[i][j+1])
            {
                if(str[i][j+2]!='\0' && str[i][j+1]==str[i][j+2])
                {
                    if(str[i][j+3]!='\0' && str[i][j+2]==str[i][j+3])
                    {
                        simple3(str[i][j]);
                        j=j+3;
                    }
                    else
                    {
                        simple2(str[i][j]);
                        j=j+2;
                    }
                }
                else
                {
                    simple1(str[i][j]);
                    j++;
                }
            }
            else
            {
                simple(str[i][j]);
            }
        }
        printf("\n");
    }
}
}
