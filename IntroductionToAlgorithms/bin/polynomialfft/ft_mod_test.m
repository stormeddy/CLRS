%   模7下的傅立叶变换
base=7;
w=3;
v=[0 1 1 1 5 2]';
fv=ft_mod_base(base,w,v)
ifv=ift_mod_base(base,w,fv)

base=17;
w=1:base;%寻找主n次单位根
n=8;
res=zeros(size(w));
for j=1:numel(res)    
        res(j)=modular_exp(j,n,base);    
end

res