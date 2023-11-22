<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="modal-add-product" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="999"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Modal Add New Product</h5>
                <a href="" data-bs-dismiss="modal"><i class="fa-solid fa-xmark"></i></a>
            </div>

            <form action="product-management" method="post">
                <div class="modal-body">
                    <div class="row form-group">
                        <div class="col-12 mb-2">
                            <fieldset>
                                <legend>Product Name</legend>
                                <input name="productName" type="text" class="form-control" value="Product Name">
                            </fieldset>
                        </div>
                        <div class="col-12 mb-2">
                            <fieldset>
                                <legend>Category</legend>
                                <Select name="categoryId">
                                    <c:forEach items="${requestScope.lstCategory}" var="c">           
                                        <option value="${c.categoryId}">${c.categoryName}</option>
                                    </c:forEach>
                                </Select>
                            </fieldset>
                        </div>
                        <div class="col-12 mb-2">
                            <fieldset>
                                <legend>Price (VND)</legend>
                                <input min="10000" name="productPrice" type="number" class="form-control" value="10000">
                            </fieldset>
                        </div>
                        <div class="col-12">
                            <fieldset>
                                <legend>Description</legend>
                                <textarea name="productDescription" class="form-control" name="" id="" cols="30" rows="10"></textarea>
                            </fieldset>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary w-100" data-bs-dismiss="modal">Add</button>
                </div>
            </form>
        </div>
    </div>
</div>