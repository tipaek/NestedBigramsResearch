#include<stdio.h>
#include<stdlib.h>

char *sol(char * s)
{
    char * r = (char *)malloc((sizeof(char) * 100));
    int l = 0;
    int ri = 0;
    for(int i = 0; s[i]; i++)
    {
        int upl = (s[i] - '0') - l;
        if (upl > 0)
        {
            for(int j = upl; j != 0; j--)
                r[ri++] = '(';
        }
        else if (upl < 0)
        {
            for(int j = upl; j != 0; j++)
                r[ri++] = ')';
        }

        l=l+upl;
        r[ri++]=s[i];
    }
    for(int i = 0; i < l; i++)
    {
        r[ri++] = ')';
    }
    r[ri] = '\0';
    return r;
}

int main()
{
    int n;
    scanf("%d", &n);
    char ** result = (char **)(malloc(sizeof(char *) * n));
    for(int i = 0; i < n; i++)
    {
        char ch[100];
        scanf("%s", ch);
        result[i] = sol(ch);
    }
    for(int i = 0; i < n; i++)
    {
        printf("Case %d:\t%s\n", i+1, result[i]);
    }
    return 0;
}