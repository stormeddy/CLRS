function fv=ft_mod_base(base,w,v)
%   模base下的傅立叶变换,base为素数
%   w为生成元,必然存在
%   v为要变换的向量
    n=numel(v);
    M=ft_mod_matrix(w,n,base)
    fv=mod(M*v,base);
end