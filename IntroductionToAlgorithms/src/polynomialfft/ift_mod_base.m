function ifv=ift_mod_base(base,w,v)
%   模base下的傅立叶逆变换
%   w为生成元
%   v为要变换的向量
    n=numel(v);
    w=modular_exp(w,n-1,base);  % 模base下的1/w    
    M=ft_mod_matrix(w,n,base);
    n=modular_exp(n,(n-1),base);% 模base下的1/n
    ifv=mod(n*M*v,base);
end