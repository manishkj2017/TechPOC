using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ShopClientDotNet.domain;

namespace ShopClientDotNet

{
    public interface ShopChannel
    {
        void Order(PetOrder order);
        Boolean IsShopClosed();
    }
}
