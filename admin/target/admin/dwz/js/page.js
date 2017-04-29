			<div class="panelBar">
				<div class="pages">
					<span>显示</span>
					<select class="combox" name="page.numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
						<option value="500" ${'500' == page.numPerPage ? 'selected="selected"' : ''}>500</option>
						<option value="1000" ${'1000' == page.numPerPage ? 'selected="selected"' : ''}>1000</option>
						<option value="3000" ${'3000' == page.numPerPage ? 'selected="selected"' : ''}>3000</option>
						<option value="9999" ${'9999' == page.numPerPage ? 'selected="selected"' : ''}>9999</option>
					</select>
					<span>条，共${page.totalCount}条</span>
				</div>
				<div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.currentPage }"></div>
			</div>